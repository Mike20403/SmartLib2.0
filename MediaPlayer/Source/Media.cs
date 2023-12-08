using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Media_Player
{
    public class Media : INotifyPropertyChanged, ICloneable
    {
        private string name;
        private Uri uri;
        private TimeSpan last_played_position;

        public event PropertyChangedEventHandler PropertyChanged;

        public TimeSpan LastPlayedPosition
        {
            get => last_played_position;
            set
            {
                last_played_position = value;
                OnPropertyChanged("last_played_position");
            }
        }
        public string Name
        {
            get => name;
            set
            {
                name = value;
                OnPropertyChanged("name");
            }
        }

        public Uri Uri_address
        {
            get => uri;
            set
            {
                uri = value;
                OnPropertyChanged("uri");
            }
        }

        public void create_Media(string _name, Uri _uri)
        {
            name = _name;
            uri = _uri;
            last_played_position = TimeSpan.Zero;
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
