package com.i5mc.idea.decompiler;

import org.benf.cfr.reader.PluginRunner;

import java.util.HashMap;
import java.util.Map;

public class CFR {

    private static final Map<String, String> OPTIONS = new HashMap<>();

    static {
        OPTIONS.put("comments", "false");
        OPTIONS.put("hideutf", "false");
    }

    private static String decompile(String path) {
        PluginRunner r = new PluginRunner(OPTIONS);
        r.addJarPath(path);
        return r.getDecompilationFor(path);
    }

    private static String decompileWithJar(String path) {
        int i = path.lastIndexOf("!/");// filesystem path can contain ! but java can not
        if (i == -1) throw new IllegalArgumentException();
        PluginRunner r = new PluginRunner(OPTIONS);
        String head = path.substring(0, i);
        r.addJarPath(head);
        String tail = path.substring(i + 2, path.length() - 6);
        return r.getDecompilationFor(tail);
    }

    public static String decompile(String protocol, String path) {
        switch (protocol) {
            case "jar":
                return decompileWithJar(path);
            case "file":
                return decompile(path);
            default:
                throw new IllegalStateException(String.format("Unknown protocol %s", protocol));
        }
    }

}
