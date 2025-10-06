import React, { useState } from 'react'
import { addActivities } from '../service/api'

function ActivityForm({ onActivitiesAdded }) {
  const [activity, setActivity] = useState({
    type: 'RUNNING',
    duration: '',
    caloriesBurn: '',
    startTime: new Date().toISOString(),
    additionalMetricsRaw: '',  // store raw string here
    additionalMetrics: {}      // parsed object here
  })

  const handleChange = (e) => {
    const { name, value } = e.target

    if (name === 'additionalMetrics') {
      setActivity(prev => ({
        ...prev,
        additionalMetricsRaw: value
      }))
    } else {
      setActivity(prev => ({
        ...prev,
        [name]: value
      }))
    }
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    
    let parsedMetrics = {}
    if (activity.additionalMetricsRaw.trim()) {
      try {
        parsedMetrics = JSON.parse(activity.additionalMetricsRaw)
      } catch (err) {
        alert('Invalid JSON in Additional Metrics')
        return
      }
    }

    const payload = {
      type: activity.type,
      duration: Number(activity.duration),
      caloriesBurn: Number(activity.caloriesBurn),
      startTime: activity.startTime,
      additionalMetrics: parsedMetrics
    }

    try {
      const res = await addActivities(payload)
      console.log('Activity submitted:', res)
      if (typeof onActivitiesAdded === 'function') onActivitiesAdded()
    } catch (error) {
      console.error(error.message)
    }
  }

  return (
    <div className="max-w-md mx-auto mt-10 p-6 bg-white rounded-lg shadow-md border border-gray-200">
      <h2 className="text-2xl font-bold text-center text-gray-800 mb-6">Log Activity</h2>

      <form onSubmit={handleSubmit} className="space-y-5">
        <div>
          <label className="block text-gray-700 font-medium mb-1">Activity Type</label>
          <select
            name="type"
            value={activity.type}
            onChange={handleChange}
            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
          >
            <option value="RUNNING">Running</option>
            <option value="WALKING">Walking</option>
            <option value="CYCLING">Cycling</option>
          </select>
        </div>

        <div>
          <label className="block text-gray-700 font-medium mb-1">Duration (minutes)</label>
          <input
            type="number"
            name="duration"
            value={activity.duration}
            onChange={handleChange}
            required
            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
            placeholder="Enter duration"
          />
        </div>

        <div>
          <label className="block text-gray-700 font-medium mb-1">Calories Burn</label>
          <input
            type="number"
            name="caloriesBurn"
            value={activity.caloriesBurn}
            onChange={handleChange}
            required
            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
            placeholder="Enter calories burned"
          />
        </div>

        <div>
          <label className="block text-gray-700 font-medium mb-1">Additional Metrics (JSON format)</label>
          <textarea
            name="additionalMetrics"
            value={activity.additionalMetricsRaw}
            onChange={handleChange}
            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
            rows="3"
            placeholder='e.g. {"heartRate": 120, "steps": 3000}'
          />
        </div>

        <div className="text-center">
          <button
            type="submit"
            className="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-lg transition duration-300"
          >
            Submit Activity
          </button>
        </div>
      </form>
    </div>
  )
}

export default ActivityForm
