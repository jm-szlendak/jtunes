package com.jms;
import com.jms.PlaylistItem;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.io.File;
import java.util.LinkedList;

/**
 * Playlist containter. Implements AbstractListModel for cooperation with JList
 */
public class Playlist extends AbstractListModel<PlaylistItem> {

    private LinkedList<PlaylistItem> playlist;
    private ListDataListener listener;
    private int currentElementIndex;
    private String lastSongDir = "C:/Users/";


    /**
     * Default constructor, creates empty list
     */
    public Playlist(){
        playlist = new LinkedList<>();
        currentElementIndex = -1;
    }


    /**
     * Adds item at the end of playlist
     * @param item Item to add
     */
    public void addPlaylistItem(PlaylistItem item){
        playlist.add(item);
        fireIntervalAdded(item, playlist.size()-1, playlist.size()-1);
    }

    /**
     * Add given item to playlist at given position
     * @param item Item to be added
     * @param index Destination
     * @return false if index negative or out of bounds, otherwise true.
     */
    public boolean addPlaylistItem(PlaylistItem item, int index)
    {
        if(index == 0)
        {
            playlist.add(index, item);
        }
        if(index > 0 && index < playlist.size()){
            playlist.add(index, item);
            fireIntervalAdded(item, index, index);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes item from playlsit
     * @param index index of item to be deleted
     * @return deleted item.
     */
    public PlaylistItem removePlaylistItem(int index)
    {
        if(index >= 0 && index <playlist.size()){
            PlaylistItem tmp = playlist.remove(index);
            if(currentElementIndex >= getSize())
                currentElementIndex = getSize() - 1;

            fireIntervalRemoved(tmp, index, index);
            return tmp;
        }
        else return null;
    }

    public int getCurrentElementIndex()
    {
        return currentElementIndex;
    }

    /**
     * Moves item from one index to another
     * @param itemIndex Source index
     * @param destination Destination index
     * @return false if one of indices is out of bounds
     */
    public boolean replaceItem(int itemIndex, int destination)
    {
        if(itemIndex < playlist.size()){
            PlaylistItem temp = playlist.remove(itemIndex);
            playlist.add(destination, temp);
            fireContentsChanged(temp, destination, -1);
            return true;
        }
        else return false;
    }

    @Override
    public int getSize() {
        return playlist.size();
    }

    @Override
    public PlaylistItem getElementAt(int index) {
        return playlist.get(index);
    }

    /**
     * Increments the list's current index for choosing the next element
     */
    public void incCurrentElementIndex()
    {
        if(currentElementIndex < this.getSize() - 1)
            this.currentElementIndex++;
    }

    /**
     * Decrements the list's current index for choosing the next element
     */
    public void decCurrentElementIndex()
    {
        if(currentElementIndex > 0)
            this.currentElementIndex--;
    }

    public void setCurrentElementIndex(int newIndex)
    {
        if(newIndex < this.getSize())
            this.currentElementIndex = newIndex;
    }

    public String getElementPath(int elementIndex)
    {
        return this.getElementAt(elementIndex).getFile().getPath();
    }

    public PlaylistItem getCurrentElement() {
        return playlist.get(currentElementIndex);
    }

    public String getLastSongDir()
    {
        return lastSongDir;
    }

    public void setLastSongDir(String lastSongDir)
    {

        this.lastSongDir = lastSongDir;
    }
}
