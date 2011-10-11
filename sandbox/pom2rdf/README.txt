
                               pom2rdf RDFizer       



  What is this?
  ------------

  This is an utility tool to convert metadata of a Maven repository into RDF,
  using Turtle (*.ttl) or RDF/XML (*.rdf) serialization formats.

  Turtle is an extension of the N-Triples test case format that carefully 
  takes the most useful and appropriate things from Notation 3 (a.k.a. N3) 
  keeping the syntax describing only RDF graphs.

  For the sake of curiosity, this tool generates a file of ~130 MBytes
  (~10 MBytes gzipped) containing ~1.3 million triples in ~5 minutes when 
  run against the whole Maven repository. 


  Requirements
  ------------
  
  This RDFizer requires:

   o  A Java 1.4 or later compatible virtual machine for your operating system.

   o  Maven 2.0 or later must be installed and the "mvn" command found
      in your shell path.
      (get it from http://maven.apache.org if you don't have it already).



  How do I use it?
  ----------------

  Once you're set (and you have the maven command 'mvn' in your path), 
  go to your command shell and type:

    mvn package

  this will download the required libraries, compile, package and prepare a
  copy of the required dependencies in the ./target directory. 
  That wasn't that painful, wasn't it?

  If you want to mirror the Maven2 repository or a part of it you can use:

    rsync -avz rsync://mirrors.ibiblio.org/maven2/jetty/* /tmp/ibiblio/jetty/
    rsync -avz rsync://mirrors.ibiblio.org/maven2/* /tmp/ibiblio/

  Now you are ready to launch it, and you can do it by typing

    (unix)  ./pom2rdf.sh [/tmp/ibiblio/] [filename] [ttl|xml]
    (win32) .\pom2rdf.bat [/tmp/ibiblio/] [filename] [ttl|xml]

  at the command line.
  
  Examples:
  
    (unix) ./pom2rdf.sh /tmp/ibiblio/ maven-dataset.ttl ttl
    (unix) ./pom2rdf.sh /tmp/ibiblio/ maven-dataset.rdf xml


                                   - o -

  This software includes software written by The Apache Software Foundation.


                                                      Paolo Castagna
