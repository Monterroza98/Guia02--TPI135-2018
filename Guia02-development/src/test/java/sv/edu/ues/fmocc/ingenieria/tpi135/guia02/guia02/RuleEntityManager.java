/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.ues.fmocc.ingenieria.tpi135.guia02.guia02;

/**
 *
 * @author joker
 */
public class RuleEntityManager implements TestRule{
    
private final Object testClass;
private final ArrayList<EntityManager> entityManagers;
public SetupTestDBRule(Object testClass) {
this.testClass = testClass;
ArrayList<Field> fields = findFieldsRequestingEntityManager();
entityManagers = createAndInjectEntityManagers(fields);
 
}
 
public Statement apply(Statement base, Description description) {
return new SetupTestDBStatement(base, entityManagers);
}
 
private ArrayList<Field> findFieldsRequestingEntityManager() {
Field[] declaredFields = testClass.getClass().getDeclaredFields();
ArrayList<Field> annotatedFields = new ArrayList<Field>();
if(declaredFields == null)
return annotatedFields;
 
Boolean faultFound = false;
for(Field field : declaredFields) {
PersistenceContext annotation = field.getAnnotation(PersistenceContext.class);
if(annotation == null)
continue;
final String unitName = annotation.unitName();
if(unitName.isEmpty()) {
System.err.println("Campo ["+ field.getName() +"] debe especificar nombre");
faultFound = true;
}
 
annotatedFields.add(field);
 
}
 
if(faultFound)
throw new RuntimeException("Campo(s anotados con errores)");
 
return annotatedFields;
}

private ArrayList<EntityManager> createAndInjectEntityManagers(ArrayList<Field> fields) {
ArrayList<EntityManager> entityManagers = new ArrayList<EntityManager>();
for(Field field : fields) {
try {
PersistenceContext annotation = field.getAnnotation(PersistenceContext.class);
EntityManager entityManager = Persistence.createEntityManagerFactory(annotation.unitName()).createEntityManager();
boolean accessible = field.isAccessible();
field.setAccessible(true);
field.set(testClass, entityManager);
field.setAccessible(accessible);
} catch (Exception ex) {
throw new RuntimeException(ex);
}
}
 
return entityManagers;
}
}
}
