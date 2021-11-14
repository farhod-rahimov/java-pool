package edu.school21.processors;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.*;
import java.util.Set;

@SupportedAnnotationTypes({"edu.school21.annotations.HtmlForm",
        "edu.school21.annotations.HtmlInput"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {

//    @Override
//    public boolean process(Set<? extends TypeElement> annotations,
//                           RoundEnvironment roundEnv) {
//        File file = new File("users_form.html");
//        FileOutputStream fileOutputStream;
//
//        try {
//            file.createNewFile();
//            FileWriter fileWriter = new FileWriter("users_form.html");
//            fileWriter.write("HELLO!");
//            fileWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        JavaFileObject jfo = null;
        Writer writer = null;

        try {
            jfo = processingEnv.getFiler().createSourceFile("hello.txt");
            writer = jfo.openWriter();
            writer.append("HELLO!");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
