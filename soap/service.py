from spyne import Application, rpc, ServiceBase
from spyne import Integer, Unicode, Array, ComplexModel, Iterable, String
from spyne.decorator import srpc
from spyne.protocol.soap import Soap11
from spyne.server.wsgi import WsgiApplication
from wsgiref.simple_server import make_server
#import re

class Person(ComplexModel):
    name = Unicode
    age = Integer


class HelloWorldService(ServiceBase):
    @rpc(Unicode, Integer, _returns=Iterable(Unicode))
    def say_hello(self, name, times):
        for i in range(times):
            yield "Hello %s, It's the %s time to meet you." % (name, i + 1)

    @rpc(Unicode, Integer, _returns=Iterable(Unicode))
    def say_hello(self,name, times):
        for i in times:
            yield 'Hello, %s' % name

        # if re.match(r'^[0-9a-zA-Z_]{0,19}@[0-9a-zA-Z]{1,13}\.[com,cn,net]{1,3}$', _url):
        #     return "Y"
        # else:
     #       return "N"

    @rpc(Array(Person), _returns=Iterable(Unicode))
    def say_hello_1(self, persons):
        print('-------say_hello_1()--------')
        if not persons:
            yield 'None'
        for person in persons:
            print('name is : %s, age is %s.' % (person.name, person.age))
            yield 'name is : %s, age is %s.' % (person.name, person.age)


class HelloWorldService2(ServiceBase):
    @rpc(Array(String), _returns=Iterable(Unicode))
    def say_hello_2(self, persons):
        if not persons:
            yield 'None'

        for person in persons:
            yield person


application = Application([HelloWorldService, HelloWorldService2],
                          'spyne.examples.hello',
                          in_protocol=Soap11(validator='lxml'),
                          out_protocol=Soap11())
wsgi_application = WsgiApplication(application)

if __name__ == '__main__':
    import logging

    host = '127.0.0.1'
    port = 8000

    logging.info("listening to http://127.0.0.1:8000")
    logging.info("wsdl is at: http://localhost:8000/?wsdl")

    server = make_server(host, port, wsgi_application)
    server.serve_forever()
