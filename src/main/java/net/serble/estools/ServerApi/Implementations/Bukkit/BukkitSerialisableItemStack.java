//package net.serble.estools.ServerApi.Implementations.Bukkit;
//
//import org.yaml.snakeyaml.DumperOptions;
//import org.yaml.snakeyaml.LoaderOptions;
//import org.yaml.snakeyaml.constructor.Construct;
//import org.yaml.snakeyaml.constructor.Constructor;
//import org.yaml.snakeyaml.introspector.Property;
//import org.yaml.snakeyaml.nodes.Node;
//import org.yaml.snakeyaml.nodes.NodeTuple;
//import org.yaml.snakeyaml.nodes.Tag;
//import org.yaml.snakeyaml.representer.Representer;
//
//import java.lang.reflect.Modifier;
//
//class BukkitSerialisableItemStack extends Constructor {
//
//    public BukkitSerialisableItemStack(Class<?> theClass) {
//        super(new LoaderOptions());
//    }
//
//
//    @Override
//    protected Class<?> getClassForNode(Node node) {
//        Class<?> clazz = super.getClassForNode(node);
//        if (clazz.getName().endsWith("CraftItemStack") && clazz.getDeclaredConstructors()[0].getModifiers() == Modifier.PROTECTED) {
//            return YourExtendedClass.class;  // YourExtendedClass is a class that extends YourClass and has a public default constructor.
//        }
//
//        return clazz;
//
//    }
//
//}
//
//
//
//class CustomRepresenter extends Representer {
//    public CustomRepresenter() {
//        super(new DumperOptions());
//        this.getPropertyUtils().setSkipMissingProperties(true);
//    }
//
//
//
//    @Override
//    protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag) {
//        if (propertyValue == null) {
//            return null; // skip null properties
//        }
//        else {
//            return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
//        }
//    }
//
//}