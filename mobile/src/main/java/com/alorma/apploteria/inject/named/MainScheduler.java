package com.alorma.apploteria.inject.named;

import javax.inject.Named;
import javax.inject.Qualifier;

@Qualifier @Named(value = "MainScheduler") public @interface MainScheduler {
}