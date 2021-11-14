package edu.school21.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RendererErrImpl implements Renderer {
    private PreProcessor preProcessor;

    public RendererErrImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    @Override
    public void renderMessage(String str) {
        str = this.preProcessor.prepareMessage(str);
        System.err.println(str);
    };
}
