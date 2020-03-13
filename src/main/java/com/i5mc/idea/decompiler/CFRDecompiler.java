package com.i5mc.idea.decompiler;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiJavaModule;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.compiled.ClassFileDecompilers;
import com.intellij.psi.impl.compiled.ClsFileImpl;
import org.jetbrains.annotations.NotNull;

public class CFRDecompiler extends ClassFileDecompilers.Light {

    @NotNull
    @Override
    public CharSequence getText(@NotNull VirtualFile input) throws CannotDecompileException {
        if (PsiPackage.PACKAGE_INFO_CLS_FILE.equals(input.getName()) || PsiJavaModule.MODULE_INFO_CLS_FILE.equals(input.getName())) {
            return ClsFileImpl.decompile(input);
        }
        return CFR.decompile(input.getFileSystem().getProtocol(), input.getPath());
    }

    @Override
    public boolean accepts(@NotNull VirtualFile file) {
        return true;
    }
}
