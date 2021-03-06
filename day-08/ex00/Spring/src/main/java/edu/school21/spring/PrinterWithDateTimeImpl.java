package edu.school21.spring;

import java.time.LocalDateTime;

public class PrinterWithDateTimeImpl implements Printer {
    private Renderer renderer;

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String str) {
        String finalStr = LocalDateTime.now().toString();

        finalStr += " " + str;
        renderer.renderMessage(finalStr);
    }
}
