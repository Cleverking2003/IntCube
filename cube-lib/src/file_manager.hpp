#pragma once

#ifdef __ANDROID__
#include <android/asset_manager_jni.h>
#else
#include <fstream>
#include <iostream>
#include <sstream>
#endif

#ifdef __ANDROID__
static thread_local AAssetManager* s_mgr;
#endif

static char* loadStringFromFile(const char* filename) {
#ifdef __ANDROID__
    auto file = AAssetManager_open(s_mgr, filename, AASSET_MODE_BUFFER);
    auto len = AAsset_getLength(file);
    auto buf = new char[len + 1];
    std::fill(buf, buf + len, 0);
    char* src = (char*)AAsset_getBuffer(file);
    std::copy(src, src + len, buf);
    return buf;
#else
    std::ifstream file(filename);

    if (!file.is_open()) {
        std::cout << "Couldn't open the file!\n";
        return nullptr;
    }

    std::stringstream buffer;
    buffer << file.rdbuf();
    auto str = buffer.str();
    file.close();
    auto c_str = new char[str.size() + 1];
    std::copy(str.begin(), str.end() + 1, c_str);
    return c_str;
#endif
}
