package edu.school21.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Program {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main1\n-----------------------------");
        main1();
        Thread.sleep(100);
        System.out.println("-----------------------------");
        System.out.println("main2\n-----------------------------");
        main2();
        Thread.sleep(100);
    }

    private static void main1() {
        PreProcessor preProcessor = new PreProcessorToUpperImpl();
        Renderer renderer = new RendererErrImpl(preProcessor);
        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
        printer.setPrefix("Prefix");
        printer.print("Hello!");
    }

    private static void main2() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml"
        );

        Printer printer = context.getBean("printerWithDateTimeBean", Printer.class);
        printer.print("Hello!");
    }
}
