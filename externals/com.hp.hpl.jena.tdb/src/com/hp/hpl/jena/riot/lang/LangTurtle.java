/*
 * (c) Copyright 2009 Hewlett-Packard Development Company, LP
 * All rights reserved.
 * [See end of file]
 */

package com.hp.hpl.jena.riot.lang;

import static com.hp.hpl.jena.riot.tokens.TokenType.DOT ;
import static com.hp.hpl.jena.riot.tokens.TokenType.EOF ;
import static com.hp.hpl.jena.riot.tokens.TokenType.NODE ;
import atlas.lib.Sink ;

import com.hp.hpl.jena.graph.Node ;
import com.hp.hpl.jena.graph.Triple ;
import com.hp.hpl.jena.riot.tokens.Tokenizer ;

/** Turtle language */
public class LangTurtle extends LangTurtleBase
{
    public LangTurtle(String baseURI, Tokenizer tokens, Sink<Triple> sink)
    {
        super(baseURI, tokens, sink) ;
    }

    @Override
    protected final void oneTopLevelElement()
    {
        // Triples node.

        // TriplesSameSubject -> TriplesNode PropertyList?
        if ( peekTriplesNodeCompound() )
        {
            if ( VERBOSE ) log.info(">> compound") ;
            Node n = triplesNodeCompound() ;
            if ( VERBOSE ) log.info("<< compound") ;
            // May be followed by: 
            //   A predicateObject list
            //   A DOT or EOF.
            if ( lookingAt(EOF) )
                return ;
            if ( lookingAt(DOT) )
            {
                nextToken() ;
                return ;
            }
            if ( peekPredicate() )
            {
                predicateObjectList(n) ;
                expectEndOfTriples() ;
                return ;
            }
            exception("Unexpected token : %s", peekToken()) ;
        }

        // TriplesSameSubject -> Term PropertyListNotEmpty 
        if ( lookingAt(NODE) )
        {
            if ( VERBOSE ) log.info(">> triples") ;
            triples() ;
            if ( VERBOSE ) log.info("<< triples") ;
            return ;
        }
        exception("Out of place: %s", peekToken()) ;
    }
}

/*
 * (c) Copyright 2009 Hewlett-Packard Development Company, LP
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