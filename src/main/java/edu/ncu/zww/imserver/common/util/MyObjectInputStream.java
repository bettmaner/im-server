package edu.ncu.zww.imserver.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;


/**
 *  自定义ObjectInputStream
 *  解决序列化对象在客户端和服务端的包名不同导致抛出ClassNotFoundException异常问题
 * */
public class MyObjectInputStream extends ObjectInputStream {

    public MyObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        String name = desc.getName();
        try {
            if ( name.startsWith(Constants.BEAN_OLD_PACKAGE) ) {
                name = name.replace( Constants.BEAN_OLD_PACKAGE, Constants.BEAN_NEW_PACKAGE );
                return Class.forName( name );
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return super.resolveClass(desc);
    }
}
