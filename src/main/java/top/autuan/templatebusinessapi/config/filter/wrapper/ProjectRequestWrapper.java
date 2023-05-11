package top.autuan.templatebusinessapi.config.filter.wrapper;


import cn.hutool.core.io.IoUtil;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * HttpWrapper
 *
 * @author Autuan.Yu
 */
public class ProjectRequestWrapper extends HttpServletRequestWrapper {
    private byte[] body;

    private BufferedReader reader;

    private ServletInputStream inputStream;

    public ProjectRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        loadBody(request);
    }

    // todo clean
    private void loadBody(HttpServletRequest request) throws IOException {
        body = IoUtil.readBytes(request.getInputStream());
//        ServletInputStream inputStream1 = request.getInputStream();
//        body = IOUtils.toByteArray(request.getInputStream());
        inputStream = new RequestCachingInputStream(this.body);
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (inputStream != null) {
            return inputStream;
        }
        return super.getInputStream();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(inputStream, getCharacterEncoding()));
        }
        return reader;
    }

    private static class RequestCachingInputStream extends ServletInputStream {

        private final ByteArrayInputStream inputStream;

        public RequestCachingInputStream(byte[] bytes) {
            inputStream = new ByteArrayInputStream(bytes);
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        @Override
        public boolean isFinished() {
            return inputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readlistener) {// NOSONAR
        }

    }
}
