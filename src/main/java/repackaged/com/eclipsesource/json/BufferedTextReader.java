/*
 * Decompiled with CFR 0_114.
 */
package repackaged.com.eclipsesource.json;

import java.io.IOException;
import java.io.Reader;

class BufferedTextReader {
    private static final int DEFAULT_BUFFERSIZE = 1024;
    private final Reader reader;
    private char[] buffer;
    private int offset;
    private int index;
    private int fill;
    private int line;
    private int lineOffset;
    private int start;
    private boolean atNewline;

    BufferedTextReader(Reader reader) {
        this(reader, 1024);
    }

    BufferedTextReader(Reader reader, int buffersize) {
        if (buffersize <= 0) {
            throw new IllegalArgumentException("Illegal buffersize: " + buffersize);
        }
        this.reader = reader;
        this.buffer = new char[buffersize];
        this.line = 1;
        this.start = -1;
    }

    public int getIndex() {
        return this.offset + this.index;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.getIndex() - this.lineOffset;
    }

    public int read() throws IOException {
        char current;
        if (this.fill == -1) {
            return -1;
        }
        if (this.index == this.fill) {
            if (this.fill == this.buffer.length) {
                if (this.start == -1) {
                    this.advanceBuffer();
                } else if (this.start == 0) {
                    this.expandBuffer();
                } else {
                    this.shiftBuffer();
                }
            }
            this.fillBuffer();
            if (this.fill == -1) {
                return -1;
            }
        }
        if (this.atNewline) {
            ++this.line;
            this.lineOffset = this.getIndex();
        }
        this.atNewline = (current = this.buffer[this.index++]) == '\n';
        return current;
    }

    public void startCapture() {
        this.start = this.index - 1;
    }

    public String endCapture() {
        if (this.start == -1) {
            return "";
        }
        int end = this.fill == -1 ? this.index : this.index - 1;
        String recorded = new String(this.buffer, this.start, end - this.start);
        this.start = -1;
        return recorded;
    }

    private void advanceBuffer() {
        this.offset += this.fill;
        this.fill = 0;
        this.index = 0;
    }

    private void shiftBuffer() {
        this.offset += this.start;
        this.fill -= this.start;
        System.arraycopy(this.buffer, this.start, this.buffer, 0, this.fill);
        this.index = this.fill;
        this.start = 0;
    }

    private void expandBuffer() {
        char[] newBuffer = new char[this.buffer.length * 2];
        System.arraycopy(this.buffer, 0, newBuffer, 0, this.fill);
        this.buffer = newBuffer;
    }

    private void fillBuffer() throws IOException {
        int read = this.reader.read(this.buffer, this.fill, this.buffer.length - this.fill);
        this.fill = read == -1 ? -1 : (this.fill += read);
    }
}

