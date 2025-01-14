/*******************************************************************************
 * Copyright (c) 2012,2020 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package ejbtest_j.ejb;

import jakarta.ejb.EJBLocalObject;

/**
 * Local interface for Enterprise Bean: TestBean
 */
public interface TestBeanLocal extends EJBLocalObject {
    int getTotal();
    int getAlive();
    int getActive();
}
