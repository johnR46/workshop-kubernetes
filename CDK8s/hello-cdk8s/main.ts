import { Construct } from 'constructs';
import { App, Chart, ChartProps } from 'cdk8s';

export class MyChart extends Chart {
  constructor(scope: Construct, id: string, props: ChartProps = { }) {
    super(scope, id, props);

    // define resources here
    const label = {app:'hello-cdk8s'};
  }

 


}

const app = new App();
new MyChart(app, 'hello-cdk8s');
app.synth();
