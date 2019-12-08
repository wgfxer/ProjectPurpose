package com.wgfxer.projectpurpose.domain;

import com.wgfxer.projectpurpose.R;

public class PurposeTheme {
    private String imagePath;
    private int gradientId;
    private float gradientAlpha;
    private boolean isWhiteFont;

    public PurposeTheme() {
        int[] gradients = new int[]{
                R.drawable.gradient1,  //исправить, если добавим новый градиент,то постоянно придется пополнять список
                R.drawable.gradient2,                       //плюс можно забыть
                R.drawable.gradient3,
                R.drawable.gradient4,
                R.drawable.gradient5,
                R.drawable.gradient6,
                R.drawable.gradient7,
                R.drawable.gradient8,
                R.drawable.gradient9,
                R.drawable.gradient10,
                R.drawable.gradient11,
                R.drawable.gradient12};
        int randomGradientIndex = (int) (Math.random() * gradients.length);
        gradientId = gradients[randomGradientIndex];
        gradientAlpha = 0.8f;
        isWhiteFont = true;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getGradientId() {
        return gradientId;
    }

    public void setGradientId(int gradientId) {
        this.gradientId = gradientId;
    }

    public float getGradientAlpha() {
        return gradientAlpha;
    }

    public void setGradientAlpha(float gradientAlpha) {
        this.gradientAlpha = gradientAlpha;
    }

    public boolean isWhiteFont() {
        return isWhiteFont;
    }

    public void setWhiteFont(boolean whiteFont) {
        isWhiteFont = whiteFont;
    }
}
