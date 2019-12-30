package springdemo.annotation;

import java.lang.annotation.Retention;

public @interface RequestMapper {
	
	String value() default "";
	
}
