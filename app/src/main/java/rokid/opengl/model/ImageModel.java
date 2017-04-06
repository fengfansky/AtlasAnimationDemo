package rokid.opengl.model;

import java.util.List;

/**
 * Created by fanfeng on 2017/3/29.
 */

public class ImageModel {

    List<FrameModel> frames;

    public List<FrameModel> getFrames() {
        return frames;
    }

    public void setFrames(List<FrameModel> frames) {
        this.frames = frames;
    }

    public static class FrameModel{
        String filename;
        Frame frame;
        boolean rotated;
        boolean trimmed;


        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public Frame getFrame() {
            return frame;
        }

        public void setFrame(Frame frame) {
            this.frame = frame;
        }

        public boolean isRotated() {
            return rotated;
        }

        public void setRotated(boolean rotated) {
            this.rotated = rotated;
        }

        public boolean isTrimmed() {
            return trimmed;
        }

        public void setTrimmed(boolean trimmed) {
            this.trimmed = trimmed;
        }

        public static class Frame{

            int x;
            int y;
            int widht;
            int height;

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getWidht() {
                return widht;
            }

            public void setWidht(int widht) {
                this.widht = widht;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }


    }



}
