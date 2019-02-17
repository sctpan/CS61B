import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {
    private Picture picture;
    double[][] energys;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        energys = new double[width()][height()];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                energys[i][j] = calEnergy(i, j);
            }
        }
    }

    public Picture picture() {
        return picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    private double calEnergy(int x, int y) {
        int width = width();
        int height = height();
        if (!(x >= 0 && x < width) || !(y >= 0 && y < height)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int leftx = x - 1 < 0 ? width - 1 : x - 1;
        int rightx = x + 1 >= width ? 0 : x + 1;
        double xGradient = gradient(picture.get(leftx, y), picture.get(rightx, y));
        int uppery = y - 1 < 0 ? height - 1 : y - 1;
        int bottomy = y + 1 >= height ? 0 : y + 1;
        double yGradient = gradient(picture.get(x, uppery), picture.get(x, bottomy));
        return xGradient + yGradient;
    }

    public double energy(int x, int y) {
        return energys[x][y];
    }

    private double gradient(Color c1, Color c2) {
        int dRed = Math.abs(c1.getRed() - c2.getRed());
        int dBlue = Math.abs(c1.getBlue() - c2.getBlue());
        int dGreen = Math.abs(c1.getGreen() - c2.getGreen());
        return (double) dRed * dRed + dBlue * dBlue + dGreen * dGreen;
    }

    public int[] findHorizontalSeam() {
        int width = width();
        int height = height();
        int[][] prev = new int[height][width];
        double[][] M = new double[height][width];
        for (int i = 0; i < height; i++) {
            M[i][0] = energy(0, i);
        }
        for (int i = 1; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double min;
                if (j == 0) {
                    min = Math.min(M[j][i - 1], M[j + 1][i - 1]);
                } else if (j == height - 1) {
                    min = Math.min(M[j][i - 1], M[j - 1][i - 1]);
                } else {
                    min = Math.min(Math.min(M[j][i - 1], M[j - 1][i - 1]), M[j + 1][i - 1]);
                }
                M[j][i] = min + energy(i, j);
                if (min == M[j][i - 1]) {
                    prev[j][i] = j;
                } else if (j > 0 && min == M[j - 1][i - 1]) {
                    prev[j][i] = j - 1;
                } else if (j < height - 1) {
                    prev[j][i] = j + 1;
                }
            }
        }
        double min = Double.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < height; i++) {
            if (min > M[i][width - 1]) {
                min = M[i][width - 1];
                index = i;
            }
        }
        int[] res = new int[width];
        int cnt = 0;
        res[cnt++] = index;
        for (int i = width - 1; i > 0; i--) {
            res[cnt++] = prev[index][i];
            index = prev[index][i];
        }
        reverse(res);
        return res;
    }           // sequence of indices for horizontal seam


    public int[] findVerticalSeam() {
        int width = width();
        int height = height();
        int[][] prev = new int[height][width];
        double[][] M = new double[height][width];
        for (int i = 0; i < width; i++) {
            M[0][i] = energy(i, 0);
        }
        for (int i = 1; i < height; i++) {
            for (int j = 0; j < width; j++) {
                double min;
                if (j == 0) {
                    min = Math.min(M[i - 1][j], M[i - 1][j + 1]);
                } else if (j == width - 1) {
                    min = Math.min(M[i - 1][j], M[i - 1][j - 1]);
                } else {
                    min = Math.min(Math.min(M[i - 1][j - 1], M[i - 1][j]), M[i - 1][j + 1]);
                }
                M[i][j] = min + energy(j, i);
                if (min == M[i - 1][j]) {
                    prev[i][j] = j;
                } else if (j > 0 && min == M[i - 1][j - 1]) {
                    prev[i][j] = j - 1;
                } else if (j < width - 1) {
                    prev[i][j] = j + 1;
                }
            }
        }
        double min = Double.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < width; i++) {
            if (min > M[height - 1][i]) {
                min = M[height - 1][i];
                index = i;
            }
        }
        int[] res = new int[height];
        int cnt = 0;
        res[cnt++] = index;
        for (int i = height - 1; i > 0; i--) {
            res[cnt++] = prev[i][index];
            index = prev[i][index];
        }
        reverse(res);
        return res;
    }

    private void reverse(int[] arr) {
        int i = 0, j = arr.length - 1;
        while (i < j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
            i++;
            j--;
        }
    }


    public void removeHorizontalSeam(int[] seam) {
        picture = SeamRemover.removeHorizontalSeam(picture, seam);
    }

    public void removeVerticalSeam(int[] seam) {
        picture = SeamRemover.removeVerticalSeam(picture, seam);
    }
}
