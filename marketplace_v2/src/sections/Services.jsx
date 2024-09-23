import React from 'react'
import {services} from '../constants/index'
import ServiceCard from '../components/ServiceCard'

const Services = () => {
  return (
    <section className="max-container flex flex-wrap justify-center gap-9">
      {services.map((service) => {
        return <ServiceCard key={service.label} {...service} />
      })}
    </section>
  )
}

export default Services