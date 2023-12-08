using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Media_Player
{
    public class Playlist : INotifyPropertyChanged, ICloneable
    {

        private string playlistName;
        private ObservableCollection<Media> media_files;

        public event PropertyChangedEventHandler PropertyChanged;

        public Playlist()
        {
            playlistName = "";
            media_files = new ObservableCollection<Media>();
        }

        public string PlaylistName
        {
            get => playlistName;
            set
            {
                playlistName = value;
                OnPropertyChanged("playlistName");
            }
        }

        public ObservableCollection<Media> MediaFile
        {
            get => media_files;
            set
            {
                media_files = value;
                OnPropertyChanged("media_files");
            }
        }

        public void add_mediaFiles(Media media)
        {
            media_files.Add(media);
        }

      
        private void OnPropertyChanged(string property = null)
        {
            if (PropertyChanged != null)
            {
                PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(property));
            }
        }

        public object Clone()
        {
            return MemberwiseClone();
        }
    }
}
