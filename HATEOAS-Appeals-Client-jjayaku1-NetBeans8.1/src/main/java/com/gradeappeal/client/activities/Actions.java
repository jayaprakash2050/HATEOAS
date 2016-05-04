/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.client.activities;

import java.util.ArrayList;

/**
 *
 * @author jayaprakashjayakumar
 */
public class Actions extends ArrayList<Activity> {

    
    private static final long serialVersionUID = 7455318429430311610L;

    public boolean has(Class clazz) {
        for(Activity act : this) {
            if(act.getClass() == clazz) {
                return true;
            }
        }
        return false;
    }

    public <T extends Activity> T get(Class clazz) {
        
        for(Activity act : this) {
            if(act.getClass() == clazz) {
                return (T) clazz.cast(act);
            }
        }
        
        return null;
    }
}
