package edu.school21.spring;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String prepareMessage(String str) {
        str = str.toUpperCase();
        return str;
    }
}
