require "bdb"

db = BDB::Hash.open("testdbm")
puts db.empty?()
puts db.each{|key, value| puts 'key:' + key + '  value:' + value}
