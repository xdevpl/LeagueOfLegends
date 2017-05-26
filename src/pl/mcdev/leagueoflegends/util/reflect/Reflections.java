package pl.mcdev.leagueoflegends.util.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import pl.mcdev.leagueoflegends.util.Logger;

public class Reflections {
	
	private static String OBC_PREFIX = Bukkit.getServer().getClass().getPackage().getName();
    private static String NMS_PREFIX = OBC_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft.server");
    private static String VERSION = OBC_PREFIX.replace("org.bukkit.craftbukkit", "").replace(".", "");
    private static Pattern MATCH_VARIABLE = Pattern.compile("\\{([^\\}]+)\\}");


    public static <T> FieldAccessor<T> getSimpleField(Class<?> target, String name) {
        return getField(target, name);
    }

    public static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<T> fieldType) {
        return getField(target, name, fieldType, 0);
    }

    public static <T> FieldAccessor<T> getField(String className, String name, Class<T> fieldType) {
        return getField(getClass(className), name, fieldType, 0);
    }

    public static <T> FieldAccessor<T> getField(Class<?> target, Class<T> fieldType, int index) {
        return getField(target, null, fieldType, index);
    }

    public static <T> FieldAccessor<T> getField(String className, Class<T> fieldType, int index) {
        return getField(getClass(className), fieldType, index);
    }

    private static <T> FieldAccessor<T> getField(Class<?> target, String name) {
        for (final Field field : target.getDeclaredFields()) {
            if ((name == null || field.getName().equals(name))) {
                field.setAccessible(true);

                return new FieldAccessor<T>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public T get(Object target) {
                        try {
                            return (T) field.get(target);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Cannot access reflection.", e);
                        }
                    }

                    @Override
                    public void set(Object target, Object value) {
                        try {
                            field.set(target, value);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Cannot access reflection.", e);
                        }
                    }

                    @Override
                    public boolean hasField(Object target) {
                        return field.getDeclaringClass().isAssignableFrom(target.getClass());
                    }
                };
            }
        }

        if (target.getSuperclass() != null)
            return getField(target.getSuperclass(), name);
        throw new IllegalArgumentException("Cannot find field with type");
    }
	public static boolean isInClass(Method[] methods, String methodName){
		for (Method m : methods)
			if (m.getName() == methodName)
				return true;
		return false;
	}

    private static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<T> fieldType, int index) {
        for (final Field field : target.getDeclaredFields()) {
            if ((name == null || field.getName().equals(name)) && fieldType.isAssignableFrom(field.getType()) && index-- <= 0) {
                field.setAccessible(true);

                return new FieldAccessor<T>() {

                    @Override
                    @SuppressWarnings("unchecked")
                    public T get(Object target) {
                        try {
                            return (T) field.get(target);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Cannot access reflection.", e);
                        }
                    }

                    @Override
                    public void set(Object target, Object value) {
                        try {
                            field.set(target, value);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Cannot access reflection.", e);
                        }
                    }

                    @Override
                    public boolean hasField(Object target) {
                        return field.getDeclaringClass().isAssignableFrom(target.getClass());
                    }
                };
            }
        }

        if (target.getSuperclass() != null)
            return getField(target.getSuperclass(), name, fieldType, index);

        throw new IllegalArgumentException("Cannot find field with type " + fieldType);
    }

    public static MethodInvoker getMethod(String className, String methodName, Class<?>... params) {
        return getTypedMethod(getClass(className), methodName, null, params);
    }

    public static MethodInvoker getMethod(Class<?> clazz, String methodName, Class<?>... params) {
        return getTypedMethod(clazz, methodName, null, params);
    }

    public static MethodInvoker getTypedMethod(Class<?> clazz, String methodName, Class<?> returnType, Class<?>... params) {
        for (final Method method : clazz.getDeclaredMethods()) {
            if ((methodName == null || method.getName().equals(methodName)) && (returnType == null) || method.getReturnType().equals(returnType) && Arrays.equals(method.getParameterTypes(), params)) {
                method.setAccessible(true);

                return new MethodInvoker() {

                    @Override
                    public Object invoke(Object target, Object... arguments) {
                        try {
                            return method.invoke(target, arguments);
                        } catch (Exception e) {
                            throw new RuntimeException("Cannot invoke method " + method, e);
                        }
                    }

                };
            }
        }

        if (clazz.getSuperclass() != null)
            return getMethod(clazz.getSuperclass(), methodName, params);

        throw new IllegalStateException(String.format("Unable to find method %s (%s).", methodName, Arrays.asList(params)));
    }

    public static ConstructorInvoker getConstructor(String className, Class<?>... params) {
        return getConstructor(getClass(className), params);
    }

    public static ConstructorInvoker getConstructor(Class<?> clazz, Class<?>... params) {

        for (final Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (Arrays.equals(constructor.getParameterTypes(), params)) {
                constructor.setAccessible(true);

                return new ConstructorInvoker() {

                    @Override
                    public Object invoke(Object... arguments) {
                        try {
                            return constructor.newInstance(arguments);
                        } catch (Exception e) {
                            throw new RuntimeException("Cannot invoke constructor " + constructor, e);
                        }
                    }

                };
            }
        }

        throw new IllegalStateException(String.format("Unable to find constructor for %s (%s).", clazz, Arrays.asList(params)));
    }

    public static Class<Object> getUntypedClass(String lookupName) {
        @SuppressWarnings({"rawtypes", "unchecked"})
        Class<Object> clazz = (Class) getClass(lookupName);
        return clazz;
    }

    public static Class<?> getClass(String lookupName) {
        return getCanonicalClass(expandVariables(lookupName));
    }

    public static Class<?> getMinecraftClass(String name) {
        return getCanonicalClass(NMS_PREFIX + "." + name);
    }

    public static Class<?> getCraftBukkitClass(String name) {
        return getCanonicalClass(OBC_PREFIX + "." + name);
    }

    private static Class<?> getCanonicalClass(String canonicalName) {
        try {
            return Class.forName(canonicalName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Cannot find " + canonicalName, e);
        }
    }

    private static String expandVariables(String name) {
        StringBuffer output = new StringBuffer();
        Matcher matcher = MATCH_VARIABLE.matcher(name);

        while (matcher.find()) {
            String variable = matcher.group(1);
            String replacement = "";

            if ("nms".equalsIgnoreCase(variable))
                replacement = NMS_PREFIX;
            else if ("obc".equalsIgnoreCase(variable))
                replacement = OBC_PREFIX;
            else if ("version".equalsIgnoreCase(variable))
                replacement = VERSION;
            else
                throw new IllegalArgumentException("Unknown variable: " + variable);

            if (replacement.length() > 0 && matcher.end() < name.length() && name.charAt(matcher.end()) != '.')
                replacement += ".";
            matcher.appendReplacement(output, Matcher.quoteReplacement(replacement));
        }

        matcher.appendTail(output);
        return output.toString();
    }

    public interface ConstructorInvoker {
        Object invoke(Object... arguments);
    }

    public interface MethodInvoker {
        Object invoke(Object target, Object... arguments);
    }

    public interface FieldAccessor<T> {
        T get(Object target);

        void set(Object target, Object value);

        boolean hasField(Object target);
    }
    
    //

	public static Class<?> getCraftClass(String name) {
		String className = "net.minecraft.server." + getVersion() + name;
		Class<?> c = null;
		try {
			c = Class.forName(className);
		} catch (Exception e) {
			if (Logger.exception(e.getCause()))
				e.printStackTrace();
		}
		return c;
	}

	public static Class<?> getBukkitClass(String name) {
		String className = "org.bukkit.craftbukkit." + getVersion() + name;
		Class<?> c = null;
		try {
			c = Class.forName(className);
		} catch (Exception e) {
			if (Logger.exception(e.getCause()))
				e.printStackTrace();
		}
		return c;
	}

	public static Class<?> getBukkitClass() {
		Class<?> c = null;
		try {
			c = Class.forName("org.bukkit.Bukkit");
		} catch (Exception e) {
		}
		return c;
	}

	public static Class<?> getPacketClass() {
		Class<?> c = null;
		try {
			c = Class.forName("org.bukkit.event.player.PlayerCommandPreprocessEvent");
		} catch (Exception e) {
		}
		return c;
	}

	public static Class<?> getPlayerClass() {
		Class<?> c = null;
		try {
			c = Class.forName("org.bukkit.event.player.PlayerEvent");
		} catch (Exception e) {
		}
		return c;
	}

	public static Object getHandle(Entity entity) {
		try {
			return getMethod(entity.getClass(), "getHandle").invoke(entity);
		} catch (Exception e) {
			if (Logger.exception(e.getCause()))
				e.printStackTrace();
			return null;
		}
	}

	public static Object getHandle(World world) {
		try {
			return getMethod(world.getClass(), "getHandle").invoke(world);
		} catch (Exception e) {
			if (Logger.exception(e.getCause()))
				e.printStackTrace();
			return null;
		}
	}

	public static Field getPrivateField(Class<?> cl, String field_name) {
		try {
			Field field = cl.getDeclaredField(field_name);
			field.setAccessible(true);
			return field;
		} catch (Exception e) {
			if (Logger.exception(e.getCause()))
				e.printStackTrace();
			return null;
		}
	}

	public static Method getMethod(Class<?> cl, String method) {
		for (Method m : cl.getMethods())
			if (m.getName().equals(method))
				return m;
		return null;
	}

	public static Method getMethodName(Class<?> cl, String method) {
		for (Method m : cl.getMethods())
			if (m.getName().contains(method))
				return m;
		return null;
	}

	public static boolean classListEqual(Class<?>[] l1, Class<?>[] l2) {
		boolean equal = true;
		if (l1.length != l2.length)
			return false;
		for (int i = 0; i < l1.length; i++)
			if (l1[i] != l2[i]) {
				equal = false;
				break;
			}
		return equal;
	}

	public static String getVersion() {
		String name = Bukkit.getServer().getClass().getPackage().getName();
		String version = name.substring(name.lastIndexOf('.') + 1) + ".";
		return version;
	}
}
