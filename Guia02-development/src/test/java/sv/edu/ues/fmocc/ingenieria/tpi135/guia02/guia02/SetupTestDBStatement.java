/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.ues.fmocc.ingenieria.tpi135.guia02.guia02;

import java.beans.Statement;

/**
 *
 * @author joker
 */
public class SetupTestDBStatement extends Statement{
private final Statement surrounded;
private final ArrayList<EntityManager> entityManagers;
 
public SetupTestDBStatement(Statement surrounded, ArrayList<EntityManager> entityManagers) {
this.surrounded = surrounded;
this.entityManagers = entityManagers;
}
 
@Override
public void evaluate() throws Throwable {
try {
for(EntityManager em : entityManagers)
em.getTransaction().begin();
surrounded.evaluate();
} catch (Exception e) {
e.printStackTrace();
System.out.println("Cerrando EntityManager");
for(EntityManager em : entityManagers)
try {
em.getTransaction().rollback();
em.close();
 
} catch(Exception ex) {
ex.printStackTrace();
}
}
}
}
    
}
