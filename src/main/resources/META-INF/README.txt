There is a central control servlet. The central control servlet invokes a
request handler to handle each request. Request handlers are named and the
name of the request handler is used to invoke it by the control servlet.

There are 2 ways to invoke a request handler named 'feeds':

With contextURL 'http://wwww.site.com/newsminute' and parameter limit = 100

a.  http://wwww.site.com/newsminute/feeds?limit=100

a.  http://wwww.site.com/newsminute/?req=feeds&limit=100

