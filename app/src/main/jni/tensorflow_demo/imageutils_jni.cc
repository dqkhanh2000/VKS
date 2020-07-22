/* Copyright 2015 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

// This file binds the native image utility code to the Java class
// which exposes them.

#include <jni.h>
#include <stdio.h>
#include <stdlib.h>

#include "prewhiten.h"

#define IMAGEUTILS_METHOD(METHOD_NAME) \
  Java_com_sict_mobile_vks_utils_ImageUtils_##METHOD_NAME  // NOLINT

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL
IMAGEUTILS_METHOD(prewhiten)(
        JNIEnv* env, jclass clazz, jfloatArray input, jint length, jobject output);

#ifdef __cplusplus
}
#endif


JNIEXPORT void JNICALL
IMAGEUTILS_METHOD(prewhiten)(
        JNIEnv* env, jclass clazz, jfloatArray input, jint length, jobject output) {
  jboolean inputCopy = JNI_FALSE;
  jfloat* const i = env->GetFloatArrayElements(input, &inputCopy);
  auto* const o = (jfloat*) env->GetDirectBufferAddress(output);

  Prewhiten(i, length, o);

  env->ReleaseFloatArrayElements(input, i, JNI_ABORT);
}
