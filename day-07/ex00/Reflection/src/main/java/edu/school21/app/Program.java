package edu.school21.app;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Classes:");
        System.out.println("User");
        System.out.println("Car");
        System.out.println("-----------------");
        System.out.println("Enter class name:");
        String className = scanner.nextLine();
        System.out.println("-----------------");

        try {
            Class someClass = Class.forName("edu.school21.models." + className);
            printFields(someClass);
            printMethods(someClass);
            System.out.println("-----------------");
            System.out.println("Let's create an object.");
            Object object = createObject(someClass, scanner);
            System.out.println("-----------------");
            Object objectBeforeEdit = object;

            do {
                object = editObject(objectBeforeEdit, scanner);
            } while (object == null);
            System.out.println("-----------------");

            boolean methodWasInvoked;

            do {
                methodWasInvoked = callMethod(object, scanner);
            } while (methodWasInvoked == false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        scanner.close();
    }

    private static void printFields(Class someClass) {
        Field[] fields = someClass.getDeclaredFields();

        System.out.println("fields: ");

        for (Field field : fields) {
            String[] type = field.getGenericType().getTypeName().split("\\.");
            System.out.println("    " + type[type.length - 1] + " " + field.getName());
        }
    }

    private static void printMethods(Class someClass) {
        Method[] methods = someClass.getDeclaredMethods();

        System.out.println("methods: ");

        for (Method method : methods) {
            String returnType[] = method.getGenericReturnType().getTypeName().split("\\.");
            System.out.print("    " +  returnType[returnType.length - 1] + " ");
            System.out.print(method.getName() + "(");
            Type parametersType[] = method.getGenericParameterTypes();

            if (parametersType.length == 0) {
                System.out.println(")");
                continue;
            }

            for (int i = 0; i < parametersType.length; i++) {
                String[] typeName = parametersType[i].getTypeName().split("\\.");

                if (i == parametersType.length - 1) {
                    System.out.println(typeName[typeName.length - 1] + ")");
                } else {
                    System.out.print(typeName[typeName.length - 1] + ", ");
                }
            }
        }
    }

    private static Object createObject(Class someClass, Scanner scanner) {
        Field[] fields = someClass.getDeclaredFields();
        Object newObject = null;

        try {
            newObject = someClass.newInstance();

            for (Field field : fields) {
                System.out.println(field.getName() + ":");
                setFieldValue(newObject, field, scanner.nextLine());
            }
            System.out.println("Object created: " + newObject);
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return newObject;
    }

    private static Object editObject(Object object, Scanner scanner) throws IllegalAccessException {
        Field[] fields = object.getClass().getDeclaredFields();

        System.out.println("Enter the name of field for changing: ");
        String requestedFieldName = scanner.nextLine();

        for (Field field : fields) {
            String[] currentFieldType = field.getGenericType().getTypeName().split("\\.");

            if (field.getName().equals(requestedFieldName)) {
                System.out.println("Enter " + currentFieldType[currentFieldType.length - 1] + " value: ");
                setFieldValue(object, field, scanner.nextLine());
                System.out.println("Object updated: " + object);
                return object;
            }
        }
        System.err.println("Error. Invalid field name.");
        return null;
    }

    private static void setFieldValue(Object object, Field field, String value) throws IllegalAccessException {
        field.setAccessible(true);

        if (field.getGenericType() == Boolean.class || field.getGenericType() == boolean.class) {
            field.setBoolean(object, Boolean.parseBoolean(value));
        } else if (field.getGenericType() == Byte.class || field.getGenericType() == byte.class) {
            field.setByte(object, Byte.parseByte(value));
        } else if (field.getGenericType() == Character.class || field.getGenericType() == char.class) {
            field.setChar(object, value.charAt(0));
        } else if (field.getGenericType() == Double.class || field.getGenericType() == double.class) {
            field.setDouble(object, Double.parseDouble(value));
        } else if (field.getGenericType() == Float.class || field.getGenericType() == float.class) {
            field.setFloat(object, Float.parseFloat(value));
        } else if (field.getGenericType() == Integer.class || field.getGenericType() == int.class) {
            field.setInt(object, Integer.parseInt(value));
        } else if (field.getGenericType() == Long.class || field.getGenericType() == long.class) {
            field.setLong(object, Long.parseLong(value));
        } else if (field.getGenericType() == Short.class || field.getGenericType() == short.class) {
            field.setShort(object, Short.parseShort(value));
        } else {
            field.set(object, value);
        }
    }

    private static boolean callMethod(Object object, Scanner scanner)
            throws InvocationTargetException, IllegalAccessException {
        Method[] methods = object.getClass().getDeclaredMethods();

        System.out.println("Enter the name of method for call: ");
        String requestedMethodName = scanner.nextLine();

        for (Method method : methods) {
            String currentMethod = getMethodNameWithParameters(method);

            if (currentMethod.equals(requestedMethodName)) {
                Object[] methodParameters = getParameterMethods(method, scanner);

                if (method.getGenericReturnType() == void.class) {
                    method.invoke(object, methodParameters);
                } else {
                    Object returnValue = method.invoke(object, methodParameters);
                    System.out.println("Method returned: " + returnValue);
                }
                return true;
            }
        }
        System.err.println("Error. Invalid method name.");
        return false;
    }

    private static String getMethodNameWithParameters(Method method) {
        String methodNameWithParams = method.getName() + "(";
        Type parametersType[] = method.getGenericParameterTypes();

        if (parametersType.length == 0) {
            methodNameWithParams += ")";
            return methodNameWithParams;
        }

        for (int i = 0; i < parametersType.length; i++) {
            String[] typeName = parametersType[i].getTypeName().split("\\.");

            if (i == parametersType.length - 1) {
                methodNameWithParams += typeName[typeName.length - 1] + ")";
            } else {
                methodNameWithParams += typeName[typeName.length - 1] + ",";
            }
        }
        return methodNameWithParams;
    }

    private static Object[] getParameterMethods(Method method, Scanner scanner) {
        List<Object> params = new ArrayList<>();
        Type parametersType[] = method.getGenericParameterTypes();

        for (int i = 0; i < parametersType.length; i++) {
            String[] valueType = parametersType[i].getTypeName().split("\\.");
            System.out.println("Enter " + valueType[valueType.length - 1] + " value:");
            params.add(getParameterValue(parametersType[i], scanner));
        }
        return params.toArray();
    }

    private static Object getParameterValue(Type valueType, Scanner scanner) {
        String value = scanner.nextLine();
        Object object;

        if (valueType== Boolean.class || valueType == boolean.class) {
            object = Boolean.parseBoolean(value);
        } else if (valueType == Byte.class || valueType == byte.class) {
            object = Byte.parseByte(value);
        } else if (valueType == Double.class || valueType == double.class) {
            object = Double.parseDouble(value);
        } else if (valueType == Float.class || valueType == float.class) {
            object = Float.parseFloat(value);
        } else if (valueType == Integer.class || valueType == int.class) {
            object = Integer.parseInt(value);
        } else if (valueType == Long.class || valueType == long.class) {
            object = Long.parseLong(value);
        } else if (valueType == Short.class || valueType == short.class) {
            object = Short.parseShort(value);
        } else {
            object = value;
        }
        return object;
    }
}
