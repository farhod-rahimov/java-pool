package edu.school21.spring;

public class PreProcessorToLowerImpl implements PreProcessor {
    @Override
    public String prepareMessage(String str) {
        str = str.toLowerCase();
        return str;
    }
}
