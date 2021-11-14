package edu.school21.spring;

public class RendererStandardImpl implements Renderer {
    private PreProcessor preProcessor;

    public RendererStandardImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    @Override
    public void renderMessage(String str) {
        str = this.preProcessor.prepareMessage(str);
        System.out.println(str);
    };
}
