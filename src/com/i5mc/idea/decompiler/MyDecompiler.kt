package com.i5mc.idea.decompiler

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiJavaModule
import com.intellij.psi.PsiPackage
import com.intellij.psi.compiled.ClassFileDecompilers
import com.intellij.psi.impl.compiled.ClsFileImpl

class MyDecompiler : ClassFileDecompilers.Light() {

    override fun accepts(input: VirtualFile): Boolean = true

    override fun getText(input: VirtualFile): CharSequence {
        if (PsiPackage.PACKAGE_INFO_CLS_FILE == input.name || PsiJavaModule.MODULE_INFO_CLS_FILE == input.name) {
            return ClsFileImpl.decompile(input)
        }
        return Impl.decompile(input.fileSystem.protocol, input.path)
    }

}