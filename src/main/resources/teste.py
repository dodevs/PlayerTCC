
import sys
from pytube import YouTube

link = sys.argv[1]
dir = sys.argv[2]
format = sys.argv[3]
isAudio = format.split(',')[0]
format = format.split(',')[1]

yt = YouTube(link)

videos = yt.streams.filter(only_audio= (True if isAudio == "True" else False), subtype=format).all()

videos[0].download(dir)


print(videos[0].default_filename)
