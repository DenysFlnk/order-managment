package com.translationagency.ordermanager.util.validation;

import com.translationagency.ordermanager.entity.User;
import com.translationagency.ordermanager.exception_handling.error.AppException;
import com.translationagency.ordermanager.exception_handling.error.EmailException;
import com.translationagency.ordermanager.exception_handling.error.IllegalRequestDataException;
import com.translationagency.ordermanager.exception_handling.error.NotFoundException;
import com.translationagency.ordermanager.to.email.EmailTo;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Persistable;
import org.springframework.util.Assert;

import java.lang.reflect.Field;

public final class ValidationUtil {

    public static <T> T checkNotFoundWithId(T object, int id) {
        if (object == null) {
            throw new NotFoundException("Not found entity with id=" + id);
        }
        return object;
    }

    public static void checkNew(Persistable<Integer> bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(Persistable<Integer> bean, int id) {
        Assert.notNull(bean.getId(), "Entity must has id");
        if (bean.getId() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static void checkIfEmailHasAnyNullField(EmailTo object) {
        Class<?> clazz = EmailTo.class;
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.get(object) == null) {
                    throw new EmailException("Email must has " + field.getName());
                }
            } catch (IllegalAccessException e) {
                throw new AppException(e.getLocalizedMessage());
            }
        }
    }

    public static void checkUserPassword(User user) {
        String check = user.getPassword().replace(" ", "");
        try {
            int minConstraint = getMinPasswordLength();
            if (check.length() < minConstraint) {
                throw new IllegalRequestDataException("Password length must be " + minConstraint + " or bigger");
            }
        } catch (NoSuchFieldException e) {
            throw new AppException(e.getLocalizedMessage());
        }
    }

    private static int getMinPasswordLength() throws NoSuchFieldException {
        Class<?> clazz = User.class;
        Field password = clazz.getDeclaredField("password");
        password.setAccessible(true);
        Size constraint = password.getAnnotation(Size.class);
        return constraint.min();
    }
}