#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_eastnine_data_di_NetworkCoreModule_baseUrl(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "https://www.gccompany.co.kr/App/json/";
    return env->NewStringUTF(hello.c_str());
}