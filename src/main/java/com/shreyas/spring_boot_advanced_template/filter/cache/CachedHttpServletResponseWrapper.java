package com.shreyas.spring_boot_advanced_template.filter.cache;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class CachedHttpServletResponseWrapper extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final ServletOutputStream servletOutputStream = new CachedServletOutputStream(outputStream);
    private final PrintWriter printWriter = new PrintWriter(servletOutputStream);

    public CachedHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return servletOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return printWriter;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (printWriter != null) {
            printWriter.flush();
        }
        if (servletOutputStream != null) {
            servletOutputStream.flush();
        }
        super.flushBuffer();
    }

    public byte[] getCopy() {
        return outputStream.toByteArray();
    }

    private class CachedServletOutputStream extends ServletOutputStream {
        private final ByteArrayOutputStream outputStream;

        public CachedServletOutputStream(ByteArrayOutputStream outputStream) {
            this.outputStream = outputStream;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
        }

        @Override
        public void write(int b) throws IOException {
            outputStream.write(b);
        }
    }
}
