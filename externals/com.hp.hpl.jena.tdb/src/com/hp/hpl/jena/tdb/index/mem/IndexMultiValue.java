/*
 * (c) Copyright 2007, 2008, 2009 Hewlett-Packard Development Company, LP
 * All rights reserved.
 * [See end of file]
 */

package com.hp.hpl.jena.tdb.index.mem;


import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import atlas.iterator.IteratorConcat;
import atlas.lib.DS;


public class IndexMultiValue<K, V>
{
    private Map<K, Set<V>> map = DS.map() ;
    
    public IndexMultiValue() {}
    
    public Set<V> get(K key)
    { 
        return map.get(key) ;
    }
    
    public void put(K key, V value)
    { 
        Set<V> x = map.get(key) ;
        if ( x == null )
        {
            x = DS.set() ;
            map.put(key, x) ;
        }
        if ( ! x.contains(value) )
            x.add(value) ;
    }
    
    public Iterator<V> flatten()
    {
        IteratorConcat<V> all = new IteratorConcat<V>() ;
        for ( K k : map.keySet() )
        {
            Set<V> x =  map.get(k) ;
            all.add(x.iterator()) ;
        }
        return all ;
    }
    
    public int size() { return map.size() ; }
    public boolean isEmpty() { return map.isEmpty() ; }
}

/*
 * (c) Copyright 2007, 2008, 2009 Hewlett-Packard Development Company, LP
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */