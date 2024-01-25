package kr.lenope1214.utils

import java.lang.reflect.Field
import java.util.Arrays
import java.util.Collections

object SuperClassReflectionUtils {
    fun getAllFields(clazz: Class<*>): List<Field> {
        val fields: MutableList<Field> = ArrayList()
        for (clazzInClasses in getAllClassesIncludingSuperClasses(clazz, true)) {
            fields.addAll(Arrays.asList(*clazzInClasses!!.declaredFields))
        }
        return fields
    }

    fun getAnnotation(
        clazz: Class<*>,
        targetAnnotation: Class<out Annotation?>?
    ): Annotation? {
        for (clazzInClasses in getAllClassesIncludingSuperClasses(clazz, false)) {
            if (clazzInClasses!!.isAnnotationPresent(targetAnnotation)) {
                return clazzInClasses.getAnnotation(targetAnnotation)
            }
        }
        return null
    }

    @Throws(Exception::class)
    fun getField(
        clazz: Class<*>,
        name: String
    ): Field {
        for (clazzInClasses in getAllClassesIncludingSuperClasses(clazz, false)) {
            for (field in clazzInClasses!!.declaredFields) {
                if (field.name == name) {
                    return clazzInClasses.getDeclaredField(name)
                }
            }
        }
        throw NoSuchFieldException()
    }

    private fun getAllClassesIncludingSuperClasses(
        clazz: Class<*>,
        fromSuper: Boolean
    ): List<Class<*>?> {
        var clazz: Class<*>? = clazz
        val classes: MutableList<Class<*>?> = ArrayList()
        while (clazz != null) {
            classes.add(clazz)
            clazz = clazz.superclass
        }
        if (fromSuper) {
            Collections.reverse(classes)
        }
        return classes
    }
}
