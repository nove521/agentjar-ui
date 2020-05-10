package com.cx.javaCompiler;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.util.Collection;
import java.util.Locale;

public class MyExceptionReporter {

    private final Collection<Diagnostic<? extends JavaFileObject>> diagnostics;

    public MyExceptionReporter(Collection<Diagnostic<? extends JavaFileObject>> list ) {
        this.diagnostics = list;
    }

    public String toReporter(){
        StringBuffer diagnosticsMessage = new StringBuffer();
        diagnostics.forEach(i -> {
            diagnosticsMessage.append("[").append(i.getKind().name()).append("] ");
            diagnosticsMessage.append("位置(").append(i.getLineNumber()).append(",").append(i.getColumnNumber()).append("): ");
            diagnosticsMessage.append(i.getMessage(Locale.getDefault())).append("\n");
        });
        return diagnosticsMessage.toString();
    }
}
