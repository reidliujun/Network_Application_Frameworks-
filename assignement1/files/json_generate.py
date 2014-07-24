'''
	use xmltodict library to export json file from xml file 
'''
import io, xmltodict, json
infile = io.open("books.xml", 'r')
outfile = io.open("books.json", 'wb')
o = xmltodict.parse( infile.read() )
json.dump( o , outfile, indent=2)