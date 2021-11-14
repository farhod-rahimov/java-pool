package edu.school21.spring;

public class PrinterWithPrefixImpl implements Printer {
    private Renderer renderer;
    private String prefix;

    public PrinterWithPrefixImpl(Renderer renderer) {
        this.renderer = renderer;
        this.prefix = null;
    }

    public PrinterWithPrefixImpl(Renderer renderer, String prefix) {
        this.renderer = renderer;
        this.prefix = prefix;
    }

    @Override
    public void print(String str) {
        if (prefix != null) {
            renderer.renderMessage(this.prefix + " " + str);
        } else {
            renderer.renderMessage(str);
        }
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
