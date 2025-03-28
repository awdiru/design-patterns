package com.gridnine.custom_classes.table.constructor.formatter;

import com.gridnine.custom_classes.table.constructor.TableConstructorConfig;

import java.util.ArrayList;
import java.util.List;

public class ContentFormatterImpl implements ContentFormatter {
    boolean isCellValueWrapping;
    boolean isWordsWrapping;
    int maxWidthColumn;

    public ContentFormatterImpl(TableConstructorConfig config) {
        isCellValueWrapping = config.isContentWrapping();
        isWordsWrapping = config.isWordsWrapping();
        maxWidthColumn = config.getMaxWidthColumn();
    }

    @Override
    public List<String> format(String text) {
        if (isCellValueWrapping && isWordsWrapping) return formatWordsWrapping(text);
        if (isCellValueWrapping) return formatWordsNotWrapping(text);
        return List.of(text);
    }

    private List<String> formatWordsWrapping(String text) {
        List<String> formattedText = new ArrayList<>();
        for (int i = 0; i < text.length(); i += maxWidthColumn)
            formattedText.add(text.substring(i, Integer.min(i + maxWidthColumn, text.length())));
        return formattedText;
    }

    private List<String> formatWordsNotWrapping(String text) {
        List<String> formattedText = new ArrayList<>();
        String[] splitText = text.split(" ");
        StringBuilder builder = new StringBuilder();

        builder.append(splitText[0]);
        for (int i = 1; i < splitText.length; i++) {
            String s = splitText[i];
            if ((builder + s).length() < maxWidthColumn)
                builder.append(" ").append(s);
            else {
                formattedText.add(builder.toString());
                builder = new StringBuilder();
                builder.append(s);
            }
        }
        formattedText.add(builder.toString());
        return formattedText;
    }
}
