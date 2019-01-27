package bot.command.basic;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface Command {

	public String name();
	public String description() default "No description";
	public ExecutorType type() default ExecutorType.BOT;
	
}
