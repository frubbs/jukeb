print "This line will be printed."
# monitor player events from MediaMonkey.  print event flag and player state info for each
# note: once started, script does not exit until MM is shut down.
import win32com.client
import pythoncom
import time
 
 
boolReps = ['F', 'T']   # hacky!
quit = False
 
class MMEventHandlers():
    def __init__(self):
        self._play_events = 0
 
    def showMM(self):
        # note: MMEventHandlers instance includes all of SDBApplication members as well
        playing = self.Player.isPlaying
        paused = self.Player.isPaused
        isong = self.Player.CurrentSongIndex
        print 'Play', boolReps[playing], '; Pause', boolReps[paused], '; iSong', isong,
        if playing:
            #print '>>', self.Player.CurrentSong.Title[:40]
            #print '>>', self.Player.CurrentSongList.Item(1).Title[:40]
            song_dict = {}
            song_records=[]
            import urllib
            for index in range(len(self.Player.CurrentSongList)):       
                
                print 'Current fruit :', self.Player.CurrentSongList.Item(index).Title[:40], 'ioioi',  self.Player.CurrentSongList.Item(index).Artist.Name[:40]
                song_name =  self.Player.CurrentSongList.Item(index).Title[:40]
                song_artist = self.Player.CurrentSongList.Item(index).Artist.Name[:40]
                idx = str(index)
                
                songStatus = "QUEUED"
                if index == isong:
                    songStatus = "PLAYING"
                if index < isong:
                    songStatus = "PLAYED"
                record = {"name":song_name, "songStatus":songStatus, "listPosition":idx, "album": {"name":song_artist, "artist":{"name":song_artist}}}
                song_records.append(record)

            song_dict["song"]=song_records

            import json
            
            import urllib2
            print json.dumps(song_records, ensure_ascii=False).encode(encoding='latin-1')
            url = 'http://jukeb2.herokuapp.com/api/party/vilma/songs'
            
            #url = 'http://localhost:8080/api/party/rafael/songs'
            #url='http://requestb.in/13xiwp71'
            data = json.dumps(song_records, ensure_ascii=False).encode(encoding='UTF-8')
            #data = urllib.urlencode(song_records)
            method = "PATCH"
            handler = urllib2.HTTPHandler()
            opener = urllib2.build_opener(handler)
            request = urllib2.Request(url, data=data)
            request.add_header("Content-Type",'application/json')
            request.get_method = lambda: method
            # try it; don't forget to catch the result
            try:
                connection = opener.open(request)
            except urllib2.HTTPError,e:
                connection = e

            if connection.code == 200:
                data = connection.read()
            else:
                print 'caca'
                print connection
        else:
            print
 
    def OnShutdown(self):   #OK
        global quit
        print '>>> SHUTDOWN >>> buh-bye' 
        quit = True
    def OnPlay(self):       #OK
        self._play_events += 1
        print "PLAY #",
        self.showMM()
    def OnPause(self):      #OK
        print "PAUS #",
        self.showMM()
 
    def OnStop(self):
        print "STOP #",
        self.showMM()
    def OnTrackEnd(self):
        print "TRKE #",
        self.showMM()
    def OnPlaybackEnd(self):
        print "PLYE #",
        self.showMM()
    def OnCompletePlaybackEnd(self):
        print "LSTE #",
        self.showMM()
    def OnSeek(self):       #OK
        print "SEEK #",
        self.showMM()
    def OnNowPlayingModified(self):     #OK
        print "LIST #",
        self.showMM()
 
    # OnTrackSkipped gets an argument
    def OnTrackSkipped(self, track):  #OK (only when playing)
        print "SKIP #",
        self.showMM()
        # the type of any argument to an event is PyIDispatch
        # here, use PyIDispatch.Invoke() to query the 'Title' attribute for printing
        print '[', track.Invoke(3,0,2,True), ']'
 
 
def monitor():
    # running the script will start MM if it's not already running
    SDB = win32com.client.DispatchWithEvents('SongsDB.SDBApplication', MMEventHandlers)
 
    print "** monitor started"
    while not quit:
        # required by this script because no other message loop running
        # if the app has its message loop (i.e., has a Windows UI), then
        # the events will arrive with no additional handling
        pythoncom.PumpWaitingMessages()
        time.sleep(0.2)
 
    # note that SDB instance includes members of of the MMEventHandlers class
    print "** monitor stopped; received " + str(SDB._play_events) + " play events"
 
if __name__ == '__main__':
        monitor()