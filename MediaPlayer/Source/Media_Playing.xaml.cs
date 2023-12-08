using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using System.Windows.Threading;
using Path = System.IO.Path;

namespace Media_Player
{
    /// <summary>
    /// Interaction logic for Media_Playing.xaml
    /// </summary>
    public partial class Media_Playing : Window
    {
        ObservableCollection<Media> current_Playlist = new ObservableCollection<Media>();

        int Selected_Media = 0;
        bool isShuffle = false;

        private bool mediaPlayerIsPlaying = true;
        private bool userIsDraggingSlider = false;
        public Media_Playing(int selected_index)
        {
            InitializeComponent();
            Selected_Media = selected_index;
        }

        void PlayMedia(int currentMedia )
        {
            //Play media
                Media.Source = current_Playlist[currentMedia].Uri_address;
                MediaState opt = MediaState.Play;
                Media.LoadedBehavior = opt;
        }
        private void Window_Loaded(object sender, RoutedEventArgs e)
        {

            current_Playlist = (ObservableCollection<Media>)((MainWindow)Application.Current.MainWindow).Media_Files.ItemsSource;
            Media.Source = current_Playlist[Selected_Media].Uri_address;

            MediaState opt = MediaState.Play;

            Media.LoadedBehavior = opt;
            string media_name = current_Playlist[Selected_Media].Name;
            Media_Name.Text = media_name.ToString();
            //Lưu vị trí phát cũ
            DispatcherTimer timer = new DispatcherTimer();
            timer.Interval = TimeSpan.FromSeconds(1);
            timer.Tick += timer_Tick;
            Media.Position = current_Playlist[Selected_Media].LastPlayedPosition;

            timer.Start();

        }
        private void timer_Tick(object sender, EventArgs e)
        {
            if ((Media.Source != null) && (Media.NaturalDuration.HasTimeSpan) && (!userIsDraggingSlider))
            {
                SeekbarControl.Minimum = 0;
                SeekbarControl.Maximum = Media.NaturalDuration.TimeSpan.TotalSeconds;
                end_Timer.Text = TimeSpan.FromSeconds(SeekbarControl.Maximum).ToString(@"hh\:mm\:ss");
                SeekbarControl.Value = Media.Position.TotalSeconds;
                
            }
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            current_Playlist[Selected_Media].LastPlayedPosition = Media.Position;

            DialogResult = false;
        }

        private void Slider_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            current_Timer.Text = TimeSpan.FromSeconds(SeekbarControl.Value).ToString(@"hh\:mm\:ss");
        }

        private void SeekbarControl_DragStarted(object sender, System.Windows.Controls.Primitives.DragStartedEventArgs e)
        {
            userIsDraggingSlider = true;
        }



        private void SeekbarControl_DragCompleted(object sender, System.Windows.Controls.Primitives.DragCompletedEventArgs e)
        {
            userIsDraggingSlider = false;
            Media.Position = TimeSpan.FromSeconds(SeekbarControl.Value);
        }

        private void VolumeControl_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            Media.Volume = (double)VolumeControl.Value;
        }

        private void Pause_Click(object sender, RoutedEventArgs e)
        {

            
            if (mediaPlayerIsPlaying == true)
            {
                mediaPlayerIsPlaying = false;
                MediaState opt = MediaState.Pause;
                Media.LoadedBehavior = opt;
                Play_button_image.Source = new BitmapImage(new Uri(@"/Images/play.png", UriKind.Relative));

            }
            else
            {
                mediaPlayerIsPlaying = true;
                MediaState opt = MediaState.Play;
                Media.LoadedBehavior = opt;
                Play_button_image.Source = new BitmapImage(new Uri(@"/Images/pause.png", UriKind.Relative));

            }


        }

        private void Previous_Click(object sender, RoutedEventArgs e)
        {
            if (current_Playlist.Count > 1)
            {
                Selected_Media--;
                if (Selected_Media < 0)
                    Selected_Media = current_Playlist.Count - 1;
                //Cap nhat thoi gian thuc

                string source_folder = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location) + "\\Source";
                string media_name = current_Playlist[Selected_Media].Name;
                string pathString = Path.Combine(source_folder, media_name);
                File.SetLastAccessTime(pathString, DateTime.Now);
                Media_Name.Text = media_name.ToString();

                PlayMedia(Selected_Media);
            }
        }

        private void Next_Click(object sender, RoutedEventArgs e)
        {
            if (current_Playlist.Count > 1)
            {
                Selected_Media++;
                if (Selected_Media == current_Playlist.Count)
                    Selected_Media = 0;
                //Cap nhat thoi gian thuc
                string source_folder = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location) + "\\Source";
                string media_name = current_Playlist[Selected_Media].Name;
                string pathString = Path.Combine(source_folder, media_name);
                File.SetLastAccessTime(pathString, DateTime.Now);
                Media_Name.Text = media_name.ToString();

                PlayMedia(Selected_Media);
            }
        }


        private void Shuffle_Click(object sender, RoutedEventArgs e)
        {
            isShuffle = !isShuffle;
            if(!isShuffle)
                shuffle_button.Background = new SolidColorBrush(Colors.Transparent);
            else
                shuffle_button.Background = new SolidColorBrush(Colors.CadetBlue);

        }

        private void Media_end(object sender, RoutedEventArgs e)
        {

            //Regular mode
            if (current_Playlist.Count > 1)
            {

                if (!isShuffle)
                {

                    Selected_Media++;
                    if (Selected_Media == current_Playlist.Count) Selected_Media = 0;
                }
                //Shuffle mode
                else
                {
                    Random rnd = new Random();
                    int ranIndex = rnd.Next(current_Playlist.Count);
                    Selected_Media = ranIndex;
                    

                }

                //Access timenow to new media
                string source_folder = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location) + "\\Source";
                string media_name = current_Playlist[Selected_Media].Name;
                string pathString = Path.Combine(source_folder, media_name);
                File.SetLastAccessTime(pathString, DateTime.Now);
                Media_Name.Text = media_name.ToString();


                PlayMedia(Selected_Media);
            }
            else
            {
                Media.Position = TimeSpan.FromSeconds(0);
            }
        }

        private void Shortcut_Key(object sender, KeyEventArgs e)
        {
            if (e.Key == Key.Tab)
            {
                e.Handled = true;
            }
            if (e.Key == Key.Right)
            {
                if (current_Playlist.Count > 1)
                {
                    Selected_Media++;
                    if (Selected_Media == current_Playlist.Count)
                        Selected_Media = 0;
                    //Cap nhat thoi gian thuc
                    string source_folder = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location) + "\\Source";
                    string media_name = current_Playlist[Selected_Media].Name;
                    string pathString = Path.Combine(source_folder, media_name);
                    File.SetLastAccessTime(pathString, DateTime.Now);
                    Media_Name.Text = media_name.ToString();

                    PlayMedia(Selected_Media);
                }
            }

            if (e.Key == Key.Left)
            {
                if (current_Playlist.Count > 1)
                {
                    Selected_Media--;
                    if (Selected_Media < 0)
                        Selected_Media = current_Playlist.Count - 1;
                    //Cap nhat thoi gian thuc

                    string source_folder = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location) + "\\Source";
                    string media_name = current_Playlist[Selected_Media].Name;
                    string pathString = Path.Combine(source_folder, media_name);
                    File.SetLastAccessTime(pathString, DateTime.Now);
                    Media_Name.Text = media_name.ToString();

                    PlayMedia(Selected_Media);
                }
            }

            if(e.Key == Key.Space)
            {
                if (mediaPlayerIsPlaying == true)
                {
                    mediaPlayerIsPlaying = false;
                    MediaState opt = MediaState.Pause;
                    Media.LoadedBehavior = opt;
                    Play_button_image.Source = new BitmapImage(new Uri(@"/Images/play.png", UriKind.Relative));

                }
                else
                {
                    mediaPlayerIsPlaying = true;
                    MediaState opt = MediaState.Play;
                    Media.LoadedBehavior = opt;
                    Play_button_image.Source = new BitmapImage(new Uri(@"/Images/pause.png", UriKind.Relative));

                }
            }


        }
    }
}
