package org.springscala

import org.springframework.batch.core.configuration.annotation.{EnableBatchProcessing, JobBuilderFactory, StepBuilderFactory, StepScope}
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.{Job, Step}
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.mapping.{BeanWrapperFieldSetMapper, DefaultLineMapper}
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.core.io.FileSystemResource
import org.springscala.configuration.{ConsoleItemWriter, EmployeeMapper}
import org.springscala.domain.Employee

@SpringBootApplication
@EnableBatchProcessing
@Configuration
class MyBatch(@Autowired var jobBuilderFactory: JobBuilderFactory , @Autowired var stepBuilderFactory: StepBuilderFactory ) {

 /* @Autowired
  private var jobLauncher:JobLauncher = _

  @Autowired
  private var job:Job = _*/

  @Bean
  def readCSVFilesJob: Job = {
    jobBuilderFactory.get("readCSVFilesJob").incrementer(new RunIdIncrementer).start(step1).build
  }

  @Bean
  def step1: Step = {
    stepBuilderFactory.get("step1").chunk[Employee, Employee](5).reader(reader).writer(writer).build
  }



  @Bean
  def reader: FlatFileItemReader[Employee] = {
    val reader = new FlatFileItemReader[Employee]
    //Set input file location
    reader.setResource(new FileSystemResource("C:\\farooq_learning\\populatedata\\input\\inputData.csv.txt"))
    //Set number of lines to skips. Use it if file has header rows.
    reader.setLinesToSkip(1)
    //Configure how each line will be parsed and mapped to different values
    reader.setLineMapper(new DefaultLineMapper[Employee] {
      setLineTokenizer(new DelimitedLineTokenizer() {
        setNames("id", "firstName", "lastName")
        setDelimiter(DelimitedLineTokenizer.DELIMITER_TAB)
      })
      println("I am here now")
      setFieldSetMapper(new BeanWrapperFieldSetMapper[Employee]())
    })
    reader
  }


  @Bean
  def writer ={ new ConsoleItemWriter[Employee]}



}
