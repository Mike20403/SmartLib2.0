using Fluent;
using Microsoft.Win32;
using System;
using System.IO;
using System.Collections.ObjectModel;
using System.Windows;
using System.Windows.Controls;
using System.Threading;
using System.Reflection;
using System.Windows.Shapes;
using Path = System.IO.Path;
using System.Diagnostics;
using System.Xml.Linq;
using System.ComponentModel;
using File = System.IO.File;
using System.Collections.Generic;

namespace Media_Player
{
    public partial class MainWindow : RibbonWindow, INotifyPropertyChanged
    {
        public MainWindow()
        {
            InitializeComponent();
            DataContext = this;
            createDirectory();
        }


        private void createDirectory()
        {
            string playlist_folder = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location) + "\\Playlist";
            string source_folder = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location) + "\\Source";
            Directory.CreateDirectory(playlist_folder);
            Directory.CreateDirectory(source_folder);
        }


        ObservableCollection<Playlist> allPlaylist = new ObservableCollection<Playlist>();
        private ObservableCollection<Media> Selected_Playlist = new ObservableCollection<Media>();
        ObservableCollection<Media> recent_Files = new ObservableCollection<Media>();

        int current_playlist_index = 0;

        public event PropertyChangedEventHandler PropertyChanged;

        private void Add_Playlist(object sender, RoutedEventArgs e)
        {
            var screen = new Add_Playlist();
            if (screen.ShowDialog() == true)
            {
                var new_playlist = (Playlist)screen.NewPlaylist.Clone(); ;
                allPlaylist.Add(new_playlist);

                string playlist_folder = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location) + "\\Playlist";
                string file_name = new_playlist.PlaylistName + ".txt";
                string pathString = Path.Combine(playlist_folder, file_name);
                if (!File.Exists(pathString))
                {
                    File.Create(pathString);
                }
                else
                {
                    file_name = new_playlist.PlaylistName + "(1).txt";
                    pathString = Path.Combine(playlist_folder, file_name);
                    File.Create(pathString);
                }
            }
            else
            {
                Title = "KHONG CO DU LIEU";
            }           
        }

        private void Add_Media(object sender, RoutedEventArgs e)
        {
            try
            {
                OpenFileDialog fd = new OpenFileDialog();

                fd.Filter = "MP3 Files (*.mp3)|*.mp3|MP4 File (*.mp4)|*.mp4|3GP File (*.3gp)|*.3gp|Audio File (*.wma)|*.wma|MOV File (*.mov)|*.mov|AVI File (*.avi)|*.avi|Flash Video(*.flv)|*.flv|Video File (*.wmv)|*.wmv|MPEG-2 File (*.mpeg)|*.mpeg|WebM Video (*.webm)|*.webm|All files (*.*)|*.*";
                fd.InitialDirectory = Environment.GetFolderPath(Environment.SpecialFolder.Desktop);
                fd.ShowDialog();

                string filename = fd.FileName;
                string source_folder = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location) + "\\Source";

                if (filename != "")
                {
                    File.Copy(filename, source_folder + "\\" + fd.SafeFileName, true);

                    string playlist_folder = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location) + "\\Playlist";
                    DirectoryInfo directory = new DirectoryInfo(playlist_folder);
                    FileInfo[] files = directory.GetFiles("*.txt");

                    foreach (FileInfo file in files)
                    {
                        if (Path.GetFileNameWithoutExtension(file.Name).Equals(allPlaylist[Playlist.SelectedIndex].PlaylistName))
                        {
                            StreamWriter sw = new StreamWriter(file.FullName, true);
                            sw.WriteLine(source_folder + "\\" + fd.SafeFileName);
                            //Close the file
                            sw.Close();

                            Media media = new Media();
                            media.create_Media(fd.SafeFileName, new Uri(source_folder + "\\" + fd.SafeFileName));
                            allPlaylist[Playlist.SelectedIndex].add_mediaFiles(media);
                            Selected_Playlist.Add(media);
                            break;
                        }
                    }
                }
                else
                {
                    MessageBox.Show("No file was chosen !");
                }
            }
            catch (Exception e1)
            {
                System.Console.WriteLine("Error Text: " + e1.Message);
            }
        }

        private void Tab_Changed(object sender, SelectionChangedEventArgs e)
        {
            if (Home_Tab.IsSelected == true)
            {
                Ribbon_Tab.Height = 30;
            }
            else
            {
                Ribbon_Tab.Height = 100;
            }
        }

        private void RibbonWindow_Loaded(object sender, RoutedEventArgs e)
        {
            Playlist.ItemsSource = allPlaylist;
            Media_Files.ItemsSource = Selected_Playlist;
            Recent_files.ItemsSource = recent_Files;
            Read_Playlist();
            Read_Recent_Files();
        }
        private void Read_Playlist()
        {
            try
            {
                string playlist_folder = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location) + "\\Playlist";
                DirectoryInfo directory = new DirectoryInfo(playlist_folder);
                string source_folder = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location) + "\\Source\\";
                FileInfo[] files = null;

                // First, process all the files directly under this folder
                try
                {
                    files = directory.GetFiles("*.txt");
                }
                // This is thrown if even one of the files requires permissions greater
                // than the application provides.
                catch (UnauthorizedAccessException e)
                {
                }

                if (files != null)
                {
                    foreach (FileInfo fi in files)
                    {
                        string[] lines = File.ReadAllLines(fi.FullName);
                        Playlist playlist = new Playlist()
                        {
                            PlaylistName = Path.GetFileNameWithoutExtension(fi.Name),
                        };

                        foreach (string name in lines)
                        {
                            Media media = new Media();
                            string new_name = name.Replace(source_folder, "");
                            media.create_Media(new_name, new Uri(source_folder + new_name));
                            playlist.add_mediaFiles(media);
                        }
                        allPlaylist.Add(playlist);
                    }
                }
                else
                {
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("Exception: " + e.Message);
            }
            finally
            {
                Console.WriteLine("Executing finally block.");
            }
        }

        private void Read_Recent_Files()
        {
            string source_folder = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location) + "\\Source";
            string[] paths = Directory.GetFiles(source_folder);

            foreach (string path in paths)
            {
                DateTime dt = File.GetLastAccessTime(path);
                DateTime now = DateTime.Now;

                if (MonthDifference(now,dt) <= 1)
                {
                    Media media = new Media();
                    media.create_Media(Path.GetFileNameWithoutExtension(path), new Uri(path));
                    recent_Files.Add(media);
                }
            }
        }
        private int MonthDifference(DateTime lValue, DateTime rValue)
        {
            return (lValue.Month - rValue.Month) + 12 * (lValue.Year - rValue.Year);
        }

        private void Delete_Playlist(object sender, RoutedEventArgs e)
        {
            int i = Playlist.SelectedIndex;
            if (i < 0) return;

            string playlist_folder = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location) + "\\Playlist";
            string file_name = allPlaylist[i].PlaylistName + ".txt";
            allPlaylist.RemoveAt(i);
            string pathString = Path.Combine(playlist_folder, file_name);

            if (File.Exists(pathString))
            {
                try
                {
                    File.Delete(pathString);
                }
                catch (IOException k)
                {
                    Console.WriteLine(k.Message);
                    return;
                }
            }

            Selected_Playlist.Clear();
        }
        private void Playlist_Click(object sender, System.Windows.Input.MouseButtonEventArgs e)
        {
            int i = Playlist.SelectedIndex;
            current_playlist_index = i;
            if (i < 0) return;
            int a = allPlaylist[i].MediaFile.Count;

            if (Selected_Playlist.Count != 0)
                Selected_Playlist.Clear();

            for (int j = 0; j < a; j++)
            {
                Media media = new Media();
                media.create_Media(allPlaylist[i].MediaFile[j].Name, allPlaylist[i].MediaFile[j].Uri_address);
                Selected_Playlist.Add(media);
            }

        }

        private void Media_Files_DoubleClick(object sender, System.Windows.Input.MouseButtonEventArgs e)
        {
            if (Media_Files.SelectedIndex < 0)
                return;

            var screen = new Media_Playing(Media_Files.SelectedIndex);
            string source_folder = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location) + "\\Source";
            string media_name = Selected_Playlist[Media_Files.SelectedIndex].Name;
            string pathString = Path.Combine(source_folder, media_name);

            File.SetLastAccessTime(pathString, DateTime.Now);
            if (screen.ShowDialog() == true)
            {
                    
            }
            else
            {

            }

        }

        private void Delete_Media(object sender, RoutedEventArgs e)
        {
            int i = Media_Files.SelectedIndex;
            Selected_Playlist.RemoveAt(i);

            try
            {
                string playlist_folder = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location) + "\\Playlist";
                DirectoryInfo directory = new DirectoryInfo(playlist_folder);
                FileInfo[] files = null;

                // First, process all the files directly under this folder
                try
                {
                    files = directory.GetFiles(allPlaylist[current_playlist_index].PlaylistName + ".txt");
                }
                // This is thrown if even one of the files requires permissions greater
                // than the application provides.
                catch (UnauthorizedAccessException UA)
                {

                }

                if (files != null)
                {
                    foreach (FileInfo fi in files)
                    {
                       
                        string[] lines = File.ReadAllLines(fi.FullName);
                        StreamWriter sw = new StreamWriter(fi.FullName, false);
                        foreach (string line in lines)
                        {
                            if (line == lines[i])
                            {
                                continue;
                            }
                            sw.WriteLine(line);
                        }
                        sw.Close();
                    }
                    allPlaylist[current_playlist_index].MediaFile.RemoveAt(i);
                }
                else
                {
                }
            }
            catch (Exception E)
            {
                Console.WriteLine("Exception: " + E.Message);
            }
            finally
            {
                Console.WriteLine("Executing finally block.");
            }
        }

    }
}
