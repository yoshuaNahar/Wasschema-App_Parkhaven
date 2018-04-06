import {
  HTTP_INTERCEPTORS,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/delay';
import 'rxjs/add/operator/mergeMap';
import 'rxjs/add/operator/materialize';
import 'rxjs/add/operator/dematerialize';
import { Injectable } from '@angular/core';

@Injectable()
export class FakeBackendInterceptor implements HttpInterceptor {

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    // wrap in delayed observable to simulate server api call
    return Observable.of(null).mergeMap(() => {

      // authenticate
      // remember-me never pressed, so it is undefined and empty
      if (request.url.endsWith('/user/info?remember-me=')
        || request.url.endsWith('/user/info?remember-me=false')
        || request.url.endsWith('/user/info?remember-me=true')) {
        //   let body = {
        //     id: user.id,
        //     email: user.email,
        //     firstName: user.firstName,
        //     lastName: user.lastName,
        //   };

        console.log('asdasdadsadsads');
        const body = 'asd';

        return Observable.of(new HttpResponse({status: 200, body: body}));
      }

      console.log('No fake backend route');

      // pass through any requests not handled above
      return next.handle(request);
    })

    // call materialize and dematerialize to ensure delay even if an error is thrown (https://github.com/Reactive-Extensions/RxJS/issues/648)
    .materialize()
    .delay(500)
    .dematerialize();
  }

}

export const FakeBackendProvider = {
  // use fake backend in place of Http service for backend-less development
  provide: HTTP_INTERCEPTORS,
  useClass: FakeBackendInterceptor,
  multi: true
};
