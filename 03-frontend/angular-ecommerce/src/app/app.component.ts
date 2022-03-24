import { Component } from '@angular/core';

@Component({
  selector: 'app-root', //the HTML selector used to bind the component to the HTML template file
  templateUrl: './app.component.html', //the HTML template file associated with the component
  styleUrls: ['./app.component.css'] // one or more CSS files associated with the component
})
export class AppComponent {
  title = 'angular-ecommerce';
}
