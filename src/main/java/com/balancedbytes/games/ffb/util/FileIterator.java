/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.util;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class FileIterator
implements Iterator<File> {
    public static FileFilter ACCEPT_ALL = new FileFilter(){

        @Override
        public boolean accept(File pathname) {
            return true;
        }
    };
    private File fStartDirectory;
    private FileFilter fFileFilter;
    private boolean fIncludeDirectories;
    private List<File> fFileList;
    private Iterator<File> fFileIterator;
    private List<File> fDirectoryList;
    private int fKnownSize;

    public FileIterator(File pStartDirectory) {
        this(pStartDirectory, true, null);
    }

    public FileIterator(File pStartDirectory, boolean pIncludeDirectories) {
        this(pStartDirectory, pIncludeDirectories, null);
    }

    public FileIterator(File pStartDirectory, boolean pIncludeDirectories, FileFilter pFileFilter) {
        if (pFileFilter == null) {
            this.setFileFilter(ACCEPT_ALL);
        } else {
            this.setFileFilter(pFileFilter);
        }
        this.setIncludeDirectories(pIncludeDirectories);
        this.setStartDirectory(pStartDirectory);
        this.reset();
    }

    protected void descend() {
        LinkedList<File> nextLevelFiles = new LinkedList<File>();
        LinkedList<File> nextLevelDirectories = new LinkedList<File>();
        for (File directory : this.fDirectoryList) {
            File[] files;
            if (this.fIncludeDirectories) {
                nextLevelFiles.add(directory);
            }
            if ((files = directory.listFiles()) == null) continue;
            for (int i = 0; i < files.length; ++i) {
                if (files[i].isFile()) {
                    if (!this.fFileFilter.accept(files[i])) continue;
                    nextLevelFiles.add(files[i]);
                    continue;
                }
                if (!files[i].isDirectory()) continue;
                nextLevelDirectories.add(files[i]);
            }
        }
        this.fFileList = nextLevelFiles;
        this.fKnownSize += nextLevelFiles.size();
        this.fDirectoryList = nextLevelDirectories;
        this.fFileIterator = this.fFileList.iterator();
    }

    public FileFilter getFileFilter() {
        return this.fFileFilter;
    }

    public File getStartDirectory() {
        return this.fStartDirectory;
    }

    @Override
    public boolean hasNext() {
        while (this.fFileIterator == null || !this.fFileIterator.hasNext() && this.fDirectoryList.size() > 0) {
            this.descend();
        }
        return this.fFileIterator.hasNext();
    }

    public boolean hasIncludeDirectories() {
        return this.fIncludeDirectories;
    }

    public int knownSize() {
        return this.fKnownSize;
    }

    @Override
    public File next() {
        if (this.hasNext()) {
            return this.fFileIterator.next();
        }
        throw new NoSuchElementException("No more files available.");
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Removing of files unsopported.");
    }

    public void reset() {
        this.fDirectoryList = new LinkedList<File>();
        this.fDirectoryList.add(this.fStartDirectory);
        this.fKnownSize = 0;
        this.hasNext();
    }

    protected void setFileFilter(FileFilter pFileFilter) {
        this.fFileFilter = pFileFilter;
    }

    protected void setIncludeDirectories(boolean pIncludeDirectories) {
        this.fIncludeDirectories = pIncludeDirectories;
    }

    protected void setStartDirectory(File pStartDirectory) {
        if (!pStartDirectory.isDirectory()) {
            throw new IllegalArgumentException("File " + pStartDirectory.getPath() + " is not a directory.");
        }
        this.fStartDirectory = pStartDirectory;
    }

    public static void main(String[] args) {
        int knownSize = 0;
        FileIterator myIterator = new FileIterator(new File(args[0]));
        while (myIterator.hasNext()) {
            if (myIterator.fKnownSize != knownSize) {
                knownSize = myIterator.knownSize();
                System.out.println("known size: " + knownSize);
            }
            System.out.println(myIterator.next());
        }
        System.out.println("known size: " + myIterator.knownSize());
    }

}

