#!/usr/bin/perl
use warnings ;
use strict ;
use DB_File ;
our (%h, $k, $v) ;
#unlink "fruit" ;
tie %h, "DB_File", "testdbm", O_RDWR|O_CREAT, 0666, $DB_HASH 
    or die "Cannot open file 'testdbm': $!\n";

# print the contents of the file
while (($k, $v) = each %h)
  { print "$k -> $v\n" }
untie %h ;
